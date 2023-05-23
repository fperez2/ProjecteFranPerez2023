package org.milaifontanals.gestiocites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.milaifontanals.classes.Login;
import org.milaifontanals.gestiocites.databinding.ActivityLoginBinding;

import viewModel.GestioViewModel;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    GestioViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        vm = new ViewModelProvider(this).get(GestioViewModel.class);
        //vm.startConnection("127.0.0.1",10000);
        vm.getLog().observe(this, new Observer<Login>() {
            @Override
            public void onChanged(Login login) {
                if(login==null)
                {
                    binding.txvError.setText("ERROR Usuari o contraseña no valids");
                }else{
                    Intent i = new Intent(LoginActivity.this, GestorCitesConcertadesActivity.class);
                    i.putExtra("SESSION_ID",login.getSession_id());
                    startActivity(i);
                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = binding.edtLoginName.getText().toString();
                String pass = binding.edtLoginPassword.getText().toString();
                vm.login(login,pass);
            }
        });
    }
}