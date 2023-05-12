using System;
using System.Collections.Generic;
using GestioInformesHorariClasses;

namespace InterficieGestioInformesHorari
{
    public interface IGestorInformesHorari
    {
        string Login(String login, String password);
        string GetNameByNIF(String NIF);
        //List<Cita> getAllCites(Metge metge);
        //void updateCita(Cita cita);
    }
}
