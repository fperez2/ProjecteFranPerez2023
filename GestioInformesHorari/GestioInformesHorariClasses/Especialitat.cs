using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioInformesHorariClasses
{
    public class Especialitat
    {
        private int codi;
        private String nom;


        public Especialitat(int codi, string nom)
        {
            Codi = codi;
            Nom = nom;
        }

        public string Nom { get => nom; set => nom = value; }
        public int Codi { get => codi; set => codi = value; }

        public override bool Equals(object obj)
        {
            return obj is Especialitat esp &&
                   codi == esp.codi;
        }

        public override int GetHashCode()
        {
            return 1877310944 + codi.GetHashCode();
        }

        public override string ToString()
        {
            return nom;
        }
    }
}
