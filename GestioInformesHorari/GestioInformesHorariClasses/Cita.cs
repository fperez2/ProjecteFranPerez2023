﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioCitesMediquesClasses
{
    class Cita
    {
        private int codiMetge;
        private String nif;
        private Metge metge;
        private Persona persona;
        private DateTime dataHora;
        private String informe;

        public Cita(int codiMetge, string nif, DateTime dataHora, string informe)
        {
            CodiMetge = codiMetge;
            NIF = nif;
            DataHora = dataHora;
            Informe = informe;
        }

        public int CodiMetge { get => codiMetge; set => codiMetge = value; }
        public string NIF { get => nif; set => nif = value; }
        public DateTime DataHora { get => dataHora; set => dataHora = value; }
        public string Informe { get => informe; set => informe = value; }

        public override bool Equals(object obj)
        {
            return obj is Cita cita &&
                   nif == cita.nif && 
                   codiMetge == cita.codiMetge && 
                   dataHora == cita.dataHora;
        }

        public override int GetHashCode()
        {
            return 1877310944 + nif.GetHashCode() + codiMetge.GetHashCode() + dataHora.GetHashCode();
        }

        public override string ToString()
        {
            return nif + "" + codiMetge.ToString() + "" + dataHora;
        }

    }
}