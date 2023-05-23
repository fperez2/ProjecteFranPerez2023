package viewModel;

import android.app.Application;
import android.app.Person;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import org.milaifontanals.classes.Cita;
import org.milaifontanals.classes.EntradaHorari;
import org.milaifontanals.classes.Especialitat;
import org.milaifontanals.classes.Login;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.Persona;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GestioViewModel extends AndroidViewModel {

    ExecutorService executor;
    private MutableLiveData<Login> log;
    private MutableLiveData<List<Cita>> cites;
    private MutableLiveData<List<Metge>> metges;
    private MutableLiveData<List<Persona>> personesMetge;
    private MutableLiveData<List<Especialitat>> especialitats;
    private MutableLiveData<List<EntradaHorari>> entradaHorari;
    private MutableLiveData<List<String>> forats;

    private String IP = "192.168.81.60"; //10.200.1.21 //192.168.1.29 //192.168.81.60
    private int PORT = 10000;

    public GestioViewModel(@NonNull Application application) {
        super(application);
        executor = Executors.newFixedThreadPool(10);
        log = new MutableLiveData<>();
        cites = new MutableLiveData<>();
        metges = new MutableLiveData<>();
        personesMetge = new MutableLiveData<>();
        especialitats = new MutableLiveData<>();
        entradaHorari = new MutableLiveData<>();
        forats = new MutableLiveData<>();
    }

    public void login(String login, String password)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket server;
                    ObjectOutputStream toServer;
                    ObjectInputStream fromServer;

                    InetAddress serverAddr = InetAddress.getByName(IP);
                    server = new Socket(serverAddr, PORT);
                    toServer = new ObjectOutputStream(server.getOutputStream());
                    fromServer = new ObjectInputStream(server.getInputStream());

                    toServer.writeObject("<<LOGIN>>");
                    toServer.flush();

                    toServer.writeObject(login);
                    toServer.flush();

                    toServer.writeObject(password);
                    toServer.flush();

                    log.postValue((Login)fromServer.readObject());
                    Log.e("LOGIN: ",((Login) fromServer.readObject()).getSession_id());
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void getCitesPersona(String session_id)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Socket server;
                    ObjectOutputStream toServer;
                    ObjectInputStream fromServer;

                    InetAddress serverAddr = InetAddress.getByName(IP);
                    server = new Socket(serverAddr, PORT);
                    toServer = new ObjectOutputStream(server.getOutputStream());
                    fromServer = new ObjectInputStream(server.getInputStream());
                    toServer.writeObject("<<CITES>>");
                    toServer.flush();
                    toServer.writeObject(session_id);
                    toServer.flush();
                    List<Cita> aux = (List<Cita>)fromServer.readObject();
                    cites.postValue(aux);
                    fromServer.close();
                    toServer.close();
                    server.close();

                }catch(IOException e)
                {
                    e.printStackTrace();;
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getAllMetges()
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Socket server;
                    ObjectOutputStream toServer;
                    ObjectInputStream fromServer;

                    InetAddress serverAddr = InetAddress.getByName(IP);
                    server = new Socket(serverAddr, PORT);
                    toServer = new ObjectOutputStream(server.getOutputStream());
                    fromServer = new ObjectInputStream(server.getInputStream());
                    toServer.writeObject("<<METGES>>");
                    toServer.flush();
                    List<Metge> aux = (List<Metge>)fromServer.readObject();
                    metges.postValue(aux);
                    fromServer.close();
                    toServer.close();
                    server.close();

                }catch(IOException e)
                {
                    e.printStackTrace();;
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getAllPersonaMetge()
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Socket server;
                    ObjectOutputStream toServer;
                    ObjectInputStream fromServer;

                    InetAddress serverAddr = InetAddress.getByName(IP);
                    server = new Socket(serverAddr, PORT);
                    toServer = new ObjectOutputStream(server.getOutputStream());
                    fromServer = new ObjectInputStream(server.getInputStream());
                    toServer.writeObject("<<PERSONAMETGE>>");
                    toServer.flush();
                    List<Persona> aux = (List<Persona>)fromServer.readObject();
                    personesMetge.postValue(aux);
                    fromServer.close();
                    toServer.close();
                    server.close();

                }catch(IOException e)
                {
                    e.printStackTrace();;
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getAllEspecialitats()
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Socket server;
                    ObjectOutputStream toServer;
                    ObjectInputStream fromServer;

                    InetAddress serverAddr = InetAddress.getByName(IP);
                    server = new Socket(serverAddr, PORT);
                    toServer = new ObjectOutputStream(server.getOutputStream());
                    fromServer = new ObjectInputStream(server.getInputStream());
                    toServer.writeObject("<<ESPECIALITATS>>");
                    toServer.flush();
                    List<Especialitat> aux = (List<Especialitat>)fromServer.readObject();
                    especialitats.postValue(aux);
                    fromServer.close();
                    toServer.close();
                    server.close();

                }catch(IOException e)
                {
                    e.printStackTrace();;
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getAllEntradaHorari()
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Socket server;
                    ObjectOutputStream toServer;
                    ObjectInputStream fromServer;

                    InetAddress serverAddr = InetAddress.getByName(IP);
                    server = new Socket(serverAddr, PORT);
                    toServer = new ObjectOutputStream(server.getOutputStream());
                    fromServer = new ObjectInputStream(server.getInputStream());
                    toServer.writeObject("<<ENTRADAHORARI>>");
                    toServer.flush();
                    List<EntradaHorari> aux = (List<EntradaHorari>)fromServer.readObject();
                    entradaHorari.postValue(aux);
                    fromServer.close();
                    toServer.close();
                    server.close();

                }catch(IOException e)
                {
                    e.printStackTrace();;
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void deleteCita(Cita cita)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Socket server;
                    ObjectOutputStream toServer;
                    ObjectInputStream fromServer;
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    server = new Socket(serverAddr, PORT);
                    toServer = new ObjectOutputStream(server.getOutputStream());
                    fromServer = new ObjectInputStream(server.getInputStream());
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

    public void reservarCita(String session_id, Date date, int codiMetge, String hora)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Socket server;
                    ObjectOutputStream toServer;
                    ObjectInputStream fromServer;
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    server = new Socket(serverAddr, PORT);
                    toServer = new ObjectOutputStream(server.getOutputStream());
                    fromServer = new ObjectInputStream(server.getInputStream());
                    toServer.writeObject("<<RESERVAR_CITA>>");
                    toServer.flush();
                    toServer.writeObject(session_id);
                    toServer.flush();
                    toServer.writeObject(date);
                    toServer.flush();
                    toServer.writeObject(codiMetge);
                    toServer.flush();
                    toServer.writeObject(hora);
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

    public void getAllForats(Date date, int codiMetge, int codiEsp)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Socket server;
                    ObjectOutputStream toServer;
                    ObjectInputStream fromServer;

                    InetAddress serverAddr = InetAddress.getByName(IP);
                    server = new Socket(serverAddr, PORT);
                    toServer = new ObjectOutputStream(server.getOutputStream());
                    fromServer = new ObjectInputStream(server.getInputStream());
                    toServer.writeObject("<<FORATS>>");
                    toServer.flush();
                    toServer.writeObject(date);
                    toServer.flush();
                    toServer.writeObject(codiMetge);
                    toServer.flush();
                    toServer.writeObject(codiEsp);
                    toServer.flush();
                    List<String> aux = (List<String>)fromServer.readObject();
                    forats.postValue(aux);
                    fromServer.close();
                    toServer.close();
                    server.close();

                }catch(IOException e)
                {
                    e.printStackTrace();;
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public MutableLiveData<Login> getLog() {
        return log;
    }

    public void setLog(MutableLiveData<Login> log) {
        this.log = log;
    }

    public MutableLiveData<List<Cita>> getCites() {
        return cites;
    }

    public void setCites(MutableLiveData<List<Cita>> cites) {
        this.cites = cites;
    }

    public MutableLiveData<List<Metge>> getMetges() {
        return metges;
    }

    public void setMetges(MutableLiveData<List<Metge>> metges) {
        this.metges = metges;
    }

    public MutableLiveData<List<Persona>> getPersonesMetges() {
        return personesMetge;
    }

    public void setPersonesMetge(MutableLiveData<List<Persona>> personesMetge) {
        this.personesMetge = personesMetge;
    }

    public MutableLiveData<List<Especialitat>> getEspecialitats() {
        return especialitats;
    }

    public void setEspecialitats(MutableLiveData<List<Especialitat>> especialitats) {
        this.especialitats = especialitats;
    }

    public MutableLiveData<List<EntradaHorari>> getEntradaHorari() {
        return entradaHorari;
    }

    public void setEntradaHorari(MutableLiveData<List<EntradaHorari>> entradaHorari) {
        this.entradaHorari = entradaHorari;
    }

    public MutableLiveData<List<String>> getForats() {
        return forats;
    }

    public void setForats(MutableLiveData<List<String>> forats) {
        this.forats = forats;
    }
}
