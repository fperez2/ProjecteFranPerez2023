package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.milaifontanals.gestiocites.R;

import java.sql.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import viewModel.GestioViewModel;


public class AdapterForats extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<String> forats;
    Context mContext;
    String session_id;
    GestioViewModel vmp;
    Date date;
    int codiMetge;


    public AdapterForats(Context c, GestioViewModel vm, List<String> all_forats, String session_id,Date date_calendar,int codimetge) {
        mContext = c;
        vmp = vm;
        forats = all_forats;
        this.session_id = session_id;
        date = date_calendar;
        codiMetge = codimetge;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.fila_nova_cita, parent, false);
        return new NovaCitaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        NovaCitaViewHolder holder = (NovaCitaViewHolder) viewHolder;
        if (position % 2 == 0) {
            int color = ContextCompat.getColor(mContext, R.color.teal_200);
            holder.itemView.setBackgroundColor(color);
        } else {
            int color = ContextCompat.getColor(mContext, R.color.cyan);
            holder.itemView.setBackgroundColor(color);
        }

        String forat = forats.get(position);

        holder.textViewHora.setText(forat);
        holder.buttonReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                vmp.reservarCita(session_id, date, codiMetge, forat);
                forats.remove(forat);
                notifyItemRemoved(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return forats.size();
    }

    private class NovaCitaViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHora;
        public Button buttonReservar;
        public NovaCitaViewHolder(View itemView) {
            super(itemView);
            textViewHora = itemView.findViewById(R.id.textViewHora);
            buttonReservar = itemView.findViewById(R.id.buttonReservar);
        }
    }

}
