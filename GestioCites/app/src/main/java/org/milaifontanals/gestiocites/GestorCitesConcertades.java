package org.milaifontanals.gestiocites;

import android.content.Context;
import android.os.Bundle;
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

public class GestorCitesConcertades extends AppCompatActivity {

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
        vm.getEspecialitats().observe(this, new Observer<List<Especialitat>>() {
            @Override
            public void onChanged(List<Especialitat> especialitats) {
                if(especialitats!=null)
                {
                    all_especialitats = new ArrayList<>(especialitats);
                }
            }
        });
        vm.getPersonesMetges().observe(this, new Observer<List<Persona>>() {
            @Override
            public void onChanged(List<Persona> persones) {
                if(persones!=null)
                {
                    persones_metge = new ArrayList<>(persones);
                }
            }
        });
        vm.getEntradaHorari().observe(this, new Observer<List<EntradaHorari>>() {
            @Override
            public void onChanged(List<EntradaHorari> entradaHorari) {
                if(entradaHorari!=null)
                {
                    all_entradaHorari = new ArrayList<>(entradaHorari);
                }
            }
        });
        vm.getCites().observe(this, new Observer<List<Cita>>() {
            @Override
            public void onChanged(List<Cita> cites) {
                if(cites!=null)
                {
                    binding.txvMissatgeError.setVisibility(View.GONE);
                    binding.rcyCites.setVisibility(View.VISIBLE);

                    cites_persona = new ArrayList<>(cites);

                    AdapterCites v = new AdapterCites(mContext,cites_persona,all_metges,persones_metge,all_especialitats,all_entradaHorari);
                    binding.rcyCites.setAdapter(v);

                }else {
                    binding.txvMissatgeError.setVisibility(View.VISIBLE);
                    binding.rcyCites.setVisibility(View.GONE);
                }
            }
        });
    }

}
