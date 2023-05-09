using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioCitesMediquesClasses
{
    class EntradaHorari
    {
        private int codiMetge;
        private int codiEspecialitat;

        private Metge codiEmpleat;
        private DateTime hora;
        private String diaSetmana;
        private Especialitat codi;

        public EntradaHorari(int codiMetge, int codiEspecialitat, DateTime hora, String diaSetmana)
        {
            CodiMetge = codiMetge;
            CodiEspecialitat = codiEspecialitat;
            Hora = hora;
            DiaSetmana = diaSetmana;
        }

        public int CodiMetge { get => codiMetge; set => codiMetge = value; }
        public int CodiEspecialitat { get => codiEspecialitat; set => codiEspecialitat = value; }
        public DateTime Hora { get => hora; set => hora = value; }
        public string DiaSetmana { get => diaSetmana; set => diaSetmana = value; }

        public override bool Equals(object obj)
        {
            return obj is EntradaHorari eh &&
                   codiMetge == eh.codiMetge &&
                   hora == eh.hora && 
                   diaSetmana == eh.diaSetmana;
        }

        public override int GetHashCode()
        {
            return 1877310944 + codiMetge.GetHashCode() + hora.GetHashCode() + diaSetmana.GetHashCode();
        }

        public override string ToString()
        {
            return codiMetge.ToString() + " " + hora.ToString() + " " + diaSetmana + " " + codiEspecialitat.ToString();
        }
    }
}
