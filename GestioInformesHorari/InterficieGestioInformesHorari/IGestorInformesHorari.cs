using System;
using System.Collections.Generic;
using GestioInformesHorariClasses;

namespace InterficieGestioInformesHorari
{
    public interface IGestorInformesHorari
    {
        int Login(String login, String password);
        string GetNameByNIF(String NIF);
        string GetNameByCodiMetge(int codiMetge);
        List<Cita> GetAllCites(int codiMetge);
        List<Cita> GetCitesActualWeek(int codiMetge);
        List<Cita> GetCitesPreviousWeek(int codiMetge);
        List<Cita> GetCitesNextWeek(int codiMetge);
        void UpdateInformeCita(Cita cita);
        List<Especialitat> GetEspecialitats(int codiMetge);
        List<EntradaHorari> GetHorari(int codiMetge);
        void InsertEntradaHorari(EntradaHorari eh);
        void UpdateEntradaHorari(EntradaHorari oldEH, EntradaHorari newEH);
        void DeleteEntradaHorari(EntradaHorari eh);
        List<Persona> GetAllPacients();
    }
}
