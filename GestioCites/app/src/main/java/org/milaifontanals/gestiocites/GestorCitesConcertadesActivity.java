package org.milaifontanals.gestiocites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.milaifontanals.classes.Cita;
import org.milaifontanals.classes.EntradaHorari;
import org.milaifontanals.classes.Especialitat;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.Persona;
import org.milaifontanals.gestiocites.databinding.ActivityCitesBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.AdapterCites;
import viewModel.GestioViewModel;

public class GestorCitesConcertadesActivity extends AppCompatActivity {

    ActivityCitesBinding binding;
    GestioViewModel vm;
    List<Cita> cites_persona = new ArrayList<Cita>();
    List<Metge> all_metges = new ArrayList<Metge>();
    List<Persona> persones_metge = new ArrayList<Persona>();
    List<Especialitat> all_especialitats = new ArrayList<Especialitat>();
    List<EntradaHorari> all_entradaHorari = new ArrayList<EntradaHorari>();
    String session_id;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCitesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        session_id = getIntent().getStringExtra("SESSION_ID");
        binding.rcyCites.setLayoutManager(new LinearLayoutManager(this));

        vm = new ViewModelProvider(this).get(GestioViewModel.class);

        vm.getMetges().observe(this, new Observer<List<Metge>>() {
            @Override
            public void onChanged(List<Metge> metges) {
                if(metges!=null)
                {
                    all_metges = new ArrayList<>(metges);
                }
            }
        });
        vm.getAllMetges();

        vm.getEspecialitats().observe(this, new Observer<List<Especialitat>>() {
            @Override
            public void onChanged(List<Especialitat> especialitats) {
                if(especialitats!=null)
                {
                    all_especialitats = new ArrayList<>(especialitats);
                }
            }
        });
        vm.getAllEspecialitats();

        vm.getPersonesMetges().observe(this, new Observer<List<Persona>>() {
            @Override
            public void onChanged(List<Persona> persones) {
                if(persones!=null)
                {
                    persones_metge = new ArrayList<>(persones);
                }
            }
        });
        vm.getAllPersonaMetge();

        vm.getEntradaHorari().observe(this, new Observer<List<EntradaHorari>>() {
            @Override
            public void onChanged(List<EntradaHorari> entradaHorari) {
                if(entradaHorari!=null)
                {
                    all_entradaHorari = new ArrayList<>(entradaHorari);
                }
            }
        });
        vm.getAllEntradaHorari();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        vm.getCites().observe(this, new Observer<List<Cita>>() {
            @Override
            public void onChanged(List<Cita> cites) {
                if(cites!=null)
                {
                    binding.txvMissatgeError.setVisibility(View.GONE);
                    binding.rcyCites.setVisibility(View.VISIBLE);

                    cites_persona = new ArrayList<>(cites);

                    AdapterCites v = new AdapterCites(mContext,cites_persona,all_metges,persones_metge,all_especialitats,all_entradaHorari,session_id);
                    binding.rcyCites.setAdapter(v);

                }else {
                    binding.txvMissatgeError.setVisibility(View.VISIBLE);
                    binding.rcyCites.setVisibility(View.GONE);
                }
            }
        });
        vm.getCitesPersona(session_id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_nova_visita) {
            Intent intent = new Intent(this, GestorNovaVisitaActivity.class);
            intent.putExtra("SESSION_ID",session_id);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
