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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adapter.AdapterCites;
import adapter.AdapterForats;
import viewModel.GestioViewModel;

public class GestorNovaVisitaActivity extends AppCompatActivity {
    private Spinner spinnerEspecialitat;
    private Spinner spinnerMetge;
    private DatePicker datePicker;
    ActivityNovaCitaBinding binding;
    GestioViewModel vm;
    List<String> llistaEspecialitats = new ArrayList<>();
    List<PersonaAux> llistaMetges = new ArrayList<>();
    List<String> all_forats = new ArrayList<>();
    List<Persona> persones_metge = new ArrayList<>();
    List<Metge> all_metges = new ArrayList<>();
    List<Especialitat> all_especialitats = new ArrayList<>();
    List<EntradaHorari> all_entradaHorari = new ArrayList<>();
    String session_id;
    Context mContext = this;
    int codiMetge;
    int codiEsp;
    java.sql.Date sqlDate;


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
                if (metges != null) {
                    all_metges = new ArrayList<>(metges);
                }
            }
        });
        vm.getAllMetges();

        vm.getPersonesMetges().observe(this, new Observer<List<Persona>>() {
            @Override
            public void onChanged(List<Persona> persones) {
                if (persones != null) {
                    persones_metge = new ArrayList<>(persones);
                    PersonaAux aux = new PersonaAux(" "," "," ");
                    llistaMetges.add(aux);
                    for (Persona p : persones_metge) {
                        aux = new PersonaAux(p.getNif(), p.getNom(), p.getCognom1());
                        llistaMetges.add(aux);
                    }
                    ArrayAdapter<PersonaAux> metgesAdapter = new ArrayAdapter<PersonaAux>(mContext, android.R.layout.simple_spinner_item, llistaMetges);
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
                if (especialitats != null) {
                    all_especialitats = new ArrayList<>(especialitats);
                    llistaEspecialitats.add(" ");
                    for (Especialitat e : all_especialitats) {
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
                if (entradaHorari != null) {
                    all_entradaHorari = new ArrayList<>(entradaHorari);
                }
            }
        });
        vm.getAllEntradaHorari();

        spinnerEspecialitat = binding.spinnerEspecialitat;
        spinnerMetge = binding.spinnerMetge;

        spinnerEspecialitat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerMetge.setSelection(0);
                String selectedItem = (String) parent.getItemAtPosition(position);
                llistaMetges.clear();

                PersonaAux aux = new PersonaAux(" "," "," ");
                llistaMetges.add(aux);
                if (selectedItem.equals(" ")) {
                    for (Persona p : persones_metge) {
                        aux = new PersonaAux(p.getNif(), p.getNom(), p.getCognom1());
                        llistaMetges.add(aux);
                    }
                } else {
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
                                aux = new PersonaAux(p.getNif(), p.getNom(), p.getCognom1());
                                llistaMetges.add(aux);
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerMetge.setSelection(0);
                llistaMetges.clear();
                PersonaAux aux = new PersonaAux(" "," "," ");
                llistaMetges.add(aux);
                for (Persona p : persones_metge) {
                    aux = new PersonaAux(p.getNif(), p.getNom(), p.getCognom1());
                    llistaMetges.add(aux);
                }
            }
        });

        datePicker = binding.datePicker;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker.init(year, month, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                PersonaAux metge = (PersonaAux)spinnerMetge.getSelectedItem();
                String esp = (String) spinnerEspecialitat.getSelectedItem();
                if(!metge.getNif().equals(" ") && !esp.equals(" ")) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    codiMetge = 0;
                    codiEsp = 0;
                    for (Metge m : all_metges) {
                        if (m.getNif().equals(metge.getNif())) {
                            codiMetge = m.getCodiEmpleat();
                        }
                    }
                    for (Especialitat e : all_especialitats) {
                        if (e.getNom().equals(esp)) {
                            codiEsp = e.getCodi();
                        }
                    }
                    Date date = calendar.getTime();
                    long milliseconds = date.getTime();
                    sqlDate = new java.sql.Date(milliseconds);
                    vm.getAllForats(sqlDate, codiMetge, codiEsp);
                }
            }
        });

        vm.getForats().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> forats) {
                if(forats!=null)
                {
                    binding.txvMissatgeError.setVisibility(View.GONE);
                    binding.rcyHoresLliures.setVisibility(View.VISIBLE);

                    all_forats = new ArrayList<>(forats);

                    AdapterForats f = new AdapterForats(mContext,vm,all_forats,session_id,sqlDate,codiMetge);
                    binding.rcyHoresLliures.setAdapter(f);

                }else {
                    binding.txvMissatgeError.setVisibility(View.VISIBLE);
                    binding.rcyHoresLliures.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_nova_visita) {
            Intent intent = new Intent(this, GestorNovaVisitaActivity.class);
            intent.putExtra("SESSION_ID", session_id);
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