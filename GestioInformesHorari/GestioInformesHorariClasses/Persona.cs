using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioInformesHorariClasses
{
    public class Persona
    {
        private String nif;
        private String nom;
        private String cognom1;
        private String cognom2;
        private DateTime dataNaix;
        private String adreca;
        private String poblacio;
        private char sexe;
        private String login;
        private String password;

        public Persona(string nif, string nom, string cognom1, string cognom2, DateTime dataNaix, string adreca, string poblacio, char sexe, string login, string password)
        {
            NIF = nif;
            Nom = nom;
            Cognom1 = cognom1;
            Cognom2 = cognom2;
            DataNaixement = dataNaix;
            Adreca = adreca;
            Poblacio = poblacio;
            Sexe = sexe;
            Login = login;
            Password = password;
        }

        public string NIF { get => nif; set => nif = value; }
        public string Nom { get => nom; set => nom = value; }
        public string Cognom1 { get => cognom1; set => cognom1 = value; }
        public string Cognom2 { get => cognom2; set => cognom2 = value; }
        public DateTime DataNaixement { get => dataNaix; set => dataNaix = value; }
        public string Adreca { get => adreca; set => adreca = value; }
        public string Poblacio { get => poblacio; set => poblacio = value; }
        public char Sexe { get => sexe; set => sexe = value; }
        public string Login { get => login; set => login = value; }
        public string Password { get => password; set => password = value; }

        public override bool Equals(object obj)
        {
            return obj is Persona persona &&
                   nif == persona.nif;
        }

        public override int GetHashCode()
        {
            return 1877310944 + nif.GetHashCode();
        }

        public override string ToString()
        {
            return nom + " " + cognom1;
        }

    }
}
