package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import org.milaifontanals.classes.Cita;
import org.milaifontanals.classes.EntradaHorari;
import org.milaifontanals.classes.Especialitat;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.Persona;
import org.milaifontanals.gestiocites.R;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

import viewModel.GestioViewModel;


public class AdapterCites extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Cita> cites;
    Context mContext;
    String session_id;
    GestioViewModel vmp;

    public AdapterCites(Context c, GestioViewModel vm, List<Cita> cites_persona, String session_id) {
        mContext = c;
        vmp = vm;
        cites = cites_persona;
        this.session_id = session_id;
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
        if (position % 2 == 0) {
            int color = ContextCompat.getColor(mContext, R.color.teal_200);
            holder.itemView.setBackgroundColor(color);
        } else {
            int color = ContextCompat.getColor(mContext, R.color.teal_700);
            holder.itemView.setBackgroundColor(color);
        }

        Cita c = cites.get(position);

        int codiMetge = c.getCodiMetge();
        String nom_especialitat = c.getNomEspecialitat();
        String name_metge = c.getNomMetge();
        Date dataCita = c.getDataHora();
        Calendar dataCitaC = Calendar.getInstance();
        dataCitaC.setTime(dataCita);
        int horaCita = dataCitaC.get(Calendar.HOUR_OF_DAY);
        int minutCita = dataCitaC.get(Calendar.MINUTE);

        holder.textViewData.setText(formatData.format(dataCita));
        holder.textViewHora.setText((horaCita < 10  ? "0" + horaCita : horaCita) + ":" + (minutCita == 0 ? minutCita + "0" : minutCita));
        holder.textViewEspecialitat.setText(nom_especialitat);
        holder.textViewMetge.setText(name_metge);

        holder.buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Confirmació de cancelació")
                        .setMessage("Estàs segur que desitges cancel·lar aquesta cita?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int position = holder.getAdapterPosition();
                                vmp.deleteCita(c);
                                cites.remove(c);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
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
        public Button buttonCancelar;
        public CitaViewHolder(View itemView) {
            super(itemView);
            textViewData = itemView.findViewById(R.id.textViewData);
            textViewHora = itemView.findViewById(R.id.textViewHora);
            textViewEspecialitat = itemView.findViewById(R.id.textViewEspecialitat);
            textViewMetge = itemView.findViewById(R.id.textViewMetge);
            buttonCancelar = itemView.findViewById(R.id.buttonCancelar);
        }
    }

}
