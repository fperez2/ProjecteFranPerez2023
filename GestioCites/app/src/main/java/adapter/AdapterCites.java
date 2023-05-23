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
    List<Metge> metges;
    List<Persona> persones;
    List<Especialitat> especialitats;
    List<EntradaHorari> entradaHorari;
    Context mContext;
    String session_id;

    private Socket server;
    ObjectOutputStream toServer;
    ObjectInputStream fromServer;
    ExecutorService executor;
    private String IP = "10.200.1.21"; // //192.168.1.29
    private int PORT = 10000;

    public AdapterCites(Context c, List<Cita> cites_persona, List<Metge> all_metges, List<Persona> persones_metge, List<Especialitat> all_especialitats, List<EntradaHorari> all_entradaHorari, String session_id) {
        mContext = c;
        cites = cites_persona;
        metges = all_metges;
        persones = persones_metge;
        especialitats = all_especialitats;
        entradaHorari = all_entradaHorari;
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
        String nom_especialitat = "";
        String nif_metge = "";
        String name_metge = "";
        Date dataCita = c.getDataHora();
        Calendar dataCitaC = Calendar.getInstance();
        dataCitaC.setTime(dataCita);
        int horaCita = dataCitaC.get(Calendar.HOUR_OF_DAY);
        int minutCita = dataCitaC.get(Calendar.MINUTE);

        holder.textViewData.setText(formatData.format(dataCita));
        holder.textViewHora.setText((horaCita < 10  ? "0" + horaCita : horaCita) + ":" + (minutCita == 0 ? minutCita + "0" : minutCita));

        for(EntradaHorari eh : entradaHorari){
            Date dataEH = eh.getHora();
            Calendar dataEHC = Calendar.getInstance();
            dataEHC.setTime(dataEH);
            int horaEH = dataEHC.get(Calendar.HOUR_OF_DAY);
            int minutEH = dataEHC.get(Calendar.MINUTE);

            if(horaCita == horaEH && minutCita == minutEH && codiMetge == eh.getCodiMetge()){
                for(Especialitat e : especialitats){
                    if(e.getCodi() == eh.getCodiEspecialitat()){
                        nom_especialitat = e.getNom();
                    }
                }
            }
        }
        holder.textViewEspecialitat.setText(nom_especialitat);

        for (Metge m : metges){
            if (m.getCodiEmpleat() == codiMetge){
                nif_metge = m.getNif();
            }
        }
        for(Persona p : persones){
            if(p.getNif().equals(nif_metge)){
                name_metge = p.getNom() + " " + p.getCognom1();
            }
        }
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
                                deleteCita(c);
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

    public void startConnection() {

        try {
            InetAddress serverAddr = InetAddress.getByName(IP);
            server = new Socket(serverAddr, PORT);
            toServer = new ObjectOutputStream(server.getOutputStream());
            fromServer = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCita(Cita cita)
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    startConnection();
                    toServer.writeObject("<<DELETE_CITA>>");
                    toServer.flush();
                    toServer.writeObject(cita);
                    toServer.flush();
                    fromServer.close();
                    toServer.close();
                    server.close();
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }

}
