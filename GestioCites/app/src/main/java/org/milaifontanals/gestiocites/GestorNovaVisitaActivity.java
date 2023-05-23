package org.milaifontanals.gestiocites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.milaifontanals.classes.EntradaHorari;
import org.milaifontanals.classes.Especialitat;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.Persona;
import org.milaifontanals.gestiocites.databinding.ActivityNovaCitaBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adapter.AdapterCites;
import viewModel.GestioViewModel;

public class GestorNovaVisitaActivity extends AppCompatActivity {
    private Spinner spinnerEspecialitat;
    private Spinner spinnerMetge;
    private DatePickerDialog datePickerDialog;
    private TextView txvDate;
    private Button buttonEscollirData;
    private Button buttonBuscarHora;
    ActivityNovaCitaBinding binding;
    GestioViewModel vm;
    List<String> llistaEspecialitats = new ArrayList<>();
    List<String> llistaMetges = new ArrayList<>();
    List<Persona> persones_metge = new ArrayList<>();
    List<Metge> all_metges = new ArrayList<>();
    List<Especialitat> all_especialitats = new ArrayList<>();
    List<EntradaHorari> all_entradaHorari = new ArrayList<>();
    String session_id;
    Context mContext = this;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,GestorCitesConcertadesActivity.class);
        intent.putExtra("SESSION_ID",session_id);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNovaCitaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        session_id = getIntent().getStringExtra("SESSION_ID");
        binding.rcyHoresLliures.setLayoutManager(new LinearLayoutManager(this));

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

        vm.getPersonesMetges().observe(this, new Observer<List<Persona>>() {
            @Override
            public void onChanged(List<Persona> persones) {
                if(persones!=null)
                {
                    persones_metge = new ArrayList<>(persones);
                    llistaMetges.add("");
                    for(Persona p : persones_metge){
                        llistaMetges.add(p.getNom() + " " + p.getCognom1());
                    }
                    ArrayAdapter<String> metgesAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, llistaMetges);
                    metgesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerMetge.setAdapter(metgesAdapter);
                    spinnerMetge.setSelection(0);
                }
            }
        });
        vm.getAllPersonaMetge();

        vm.getEspecialitats().observe(this, new Observer<List<Especialitat>>() {
            @Override
            public void onChanged(List<Especialitat> especialitats) {
                if(especialitats!=null)
                {
                    all_especialitats = new ArrayList<>(especialitats);
                    llistaEspecialitats.add("");
                    for(Especialitat e : all_especialitats){
                        llistaEspecialitats.add(e.getNom());
                    }
                    ArrayAdapter<String> especialitatsAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, llistaEspecialitats);
                    especialitatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEspecialitat.setAdapter(especialitatsAdapter);
                    spinnerEspecialitat.setSelection(0);
                }
            }
        });
        vm.getAllEspecialitats();

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

        spinnerEspecialitat = findViewById(R.id.spinnerEspecialitat);
        spinnerMetge = findViewById(R.id.spinnerMetge);
        txvDate = findViewById(R.id.txvDate);

        // Configurar el DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txvDate.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        }, year, month, dayOfMonth);

        buttonEscollirData = findViewById(R.id.buttonEscollirData);
        buttonEscollirData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        buttonBuscarHora = findViewById(R.id.buttonBuscarHores);
        buttonBuscarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crear nuevo adapter
                //Query para forats

                //AdapterCites adapter = new AdapterCites(mContext,cites_persona,all_metges,persones_metge,all_especialitats,all_entradaHorari,session_id);
                //binding.rcyHoresLliures.setAdapter(adapter);
            }
        });

        spinnerEspecialitat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                llistaMetges.clear();

                if(selectedItem.equals("")){
                    for(Persona p : persones_metge){
                        llistaMetges.add(p.getNom() + " " + p.getCognom1());
                    }
                }else {
                    int codiEsp = 0;
                    List<Integer> codiMetges = new ArrayList<>();
                    List<String> nifMetges = new ArrayList<>();
                    for (Especialitat e : all_especialitats) {
                        if (e.getNom().equals(selectedItem)) {
                            codiEsp = e.getCodi();
                        }
                    }
                    for (EntradaHorari eh : all_entradaHorari) {
                        if (eh.getCodiEspecialitat() == codiEsp) {
                            codiMetges.add(eh.getCodiMetge());
                        }
                    }

                    // Eliminar duplicados utilizando un HashSet
                    Set<Integer> miSet = new HashSet<>(codiMetges);
                    codiMetges.clear();
                    codiMetges.addAll(miSet);

                    for (Metge m : all_metges) {
                        for (int codiMetge : codiMetges) {
                            if (m.getCodiEmpleat() == codiMetge) {
                                nifMetges.add(m.getNif());
                            }
                        }
                    }
                    for (Persona p : persones_metge) {
                        for (String nifs : nifMetges) {
                            if (nifs.equals(p.getNif())) {
                                llistaMetges.add(p.getNom() + " " + p.getCognom1());
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                llistaMetges.clear();
                for(Persona p : persones_metge){
                    llistaMetges.add(p.getNom() + " " + p.getCognom1());
                }
            }
        });

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