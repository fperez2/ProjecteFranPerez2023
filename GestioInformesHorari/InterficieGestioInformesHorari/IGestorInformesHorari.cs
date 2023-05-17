using System;
using System.Collections.Generic;
using GestioInformesHorariClasses;

namespace InterficieGestioInformesHorari
{
    public interface IGestorInformesHorari
    {
        int Login(String login, String password);
        string GetNameByNIF(String NIF);
        List<Cita> GetAllCites(int codiMetge);
        List<Cita> GetCitesActualWeek(int codiMetge);
        List<Cita> GetCitesPreviousWeek(int codiMetge);
        List<Cita> GetCitesNextWeek(int codiMetge);
        void UpdateInformeCita(Cita cita);
        List<Especialitat> GetEspecialitats(int codiMetge);
        List<EntradaHorari> GetHorari(int codiMetge);
    }
}
