package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.milaifontanals.classes.Cita;
import org.milaifontanals.classes.EntradaHorari;
import org.milaifontanals.classes.Especialitat;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.Persona;
import org.milaifontanals.gestiocites.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AdapterCites extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Cita> cites;
    List<Metge> metges;
    List<Persona> persones;
    List<Especialitat> especialitats;
    List<EntradaHorari> entradaHorari;
    Context mContext;
    public AdapterCites(Context c, List<Cita> cites_persona, List<Metge> all_metges, List<Persona> persones_metge, List<Especialitat> all_especialitats, List<EntradaHorari> all_entradaHorari) {
        mContext = c;
        cites = cites_persona;
        metges = all_metges;
        persones = persones_metge;
        especialitats = all_especialitats;
        entradaHorari = all_entradaHorari;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.fila_cita_concertada, parent, false);
        return new CitaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");

        CitaViewHolder holder = (CitaViewHolder) viewHolder;
        Cita c = (Cita) cites.get(position);

        holder.textViewData.setText(formatData.format(c.getDataHora()));
        holder.textViewHora.setText(formatHora.format(c.getDataHora()));

        int codiMetge = c.getCodiMetge();
        String nom_especialitats = "";
        String nif_metge = "";
        String name_metge = "";
        Date dataCita = c.getDataHora();

        int horaCita = dataCita.getHours();
        int minutCita = dataCita.getMinutes();

        int codiEsp;

        for(EntradaHorari eh : entradaHorari){
            if(horaCita == eh.getHora().getHours() && minutCita == eh.getHora().getMinutes()){
                //Falta comprobar el Metge
                codiEsp = eh.getCodi().getCodi();
            }
        }
        //holder.textViewEspecialitat.setText();

        for (Metge m : metges){
            if (m.getCodiEmpleat() == codiMetge){
                nif_metge = m.getNif();
            }
        }
        for(Persona p : persones){
            if(p.getNif().equals(nif_metge)){
                name_metge = p.getNom() + p.getCognom1();
            }
        }
        holder.textViewMetge.setText(name_metge);

    }

    @Override
    public int getItemCount() {
        return cites.size();
    }

    private class CitaViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewData;
        public TextView textViewHora;
        public TextView textViewEspecialitat;
        public TextView textViewMetge;
        public CitaViewHolder(View itemView) {
            super(itemView);
            textViewData = itemView.findViewById(R.id.textViewData);
            textViewHora = itemView.findViewById(R.id.textViewHora);
            textViewEspecialitat = itemView.findViewById(R.id.textViewEspecialitat);
            textViewMetge = itemView.findViewById(R.id.textViewMetge);

        }
    }

}
