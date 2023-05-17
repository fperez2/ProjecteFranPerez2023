using System;
using System.Data;
using System.Data.Common;
using GestioInformesHorariClasses;
using GestioInformesHorariBD.BDContext;
using InterficieGestioInformesHorari;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;

namespace GestioInformesHorariBD
{
    public class EPGestioInformesHorari : IGestorInformesHorari
    {
        public EPGestioInformesHorari() { }
        public int Login(string login, string password)
        {
            try
            {
                using (MyDBContext context = new MyDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (DbCommand consulta = connexio.CreateCommand())
                        {
                            consulta.CommandText = $@"select m.Metge_CodiEmpleat from Persona p
                                                        join metge m on m.Metge_Persona_NIF = p.Persona_NIF
                                                        where p.Persona_Login = @LOGIN and p.Persona_Password = @PASSWORD";
                            DBUtil.crearParametre(consulta, "@LOGIN", login, DbType.String);
                            DBUtil.crearParametre(consulta, "@PASSWORD", password, DbType.String);
                            int resposta = (int)consulta.ExecuteScalar();

                            return resposta;
                        }
                    }
                }
            }
            catch (Exception)
            {
            }
            return 0;
        }

        public List<Especialitat> GetEspecialitats(int codiMetge)
        {
            try
            {
                using (MyDBContext context = new MyDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (DbCommand consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText = $@"select * from Especialitat e
                                                    join MetgeEspecialitat me on me.Especialitat_Codi = e.Especialitat_Codi
                                                    where me.Metge_CodiEmpleat = @codiMetge";
                            DBUtil.crearParametre(consulta, "@codiMetge", codiMetge, DbType.Int32);
                            DbDataReader resposta = consulta.ExecuteReader();

                            List<Especialitat> especialitats = new List<Especialitat>();
                            Especialitat nd = new Especialitat(0, "N/D");
                            especialitats.Add(nd);
                            while (resposta.Read())
                            {
                                int codi = resposta.GetInt32(resposta.GetOrdinal("Especialitat_Codi"));
                                string nom = resposta.GetString(resposta.GetOrdinal("Especialitat_Nom"));
                                Especialitat e = new Especialitat(codi, nom);
                                especialitats.Add(e);
                            }
                            return especialitats;
                        }
                    }
                }
            }
            catch (Exception)
            {
            }
            return null;
        }

        public string GetNameByNIF(string NIF)
        {
            try
            {
                using (MyDBContext context = new MyDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (DbCommand consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText = $@"select * from Persona where Persona_NIF = @NIF";
                            DBUtil.crearParametre(consulta, "@NIF", NIF, DbType.String);
                            DbDataReader resposta = consulta.ExecuteReader();

                            while (resposta.Read())
                            {
                                String cognom2 = null;
                                String nom = resposta.GetString(resposta.GetOrdinal("Persona_Nom"));
                                String cognom1 = resposta.GetString(resposta.GetOrdinal("Persona_Cognom1"));
                                DateTime dataNaix = resposta.GetDateTime(resposta.GetOrdinal("Persona_DataNaix"));
                                if (!resposta.IsDBNull(resposta.GetOrdinal("Persona_Cognom2")))
                                {
                                    cognom2 = resposta.GetString(resposta.GetOrdinal("Persona_Cognom2"));
                                }
                                string nomComplet = nom + " " + cognom1 + (cognom2 != null ? " " + cognom2 : "");

                                return nomComplet + ";" + dataNaix;
                            }
                            return null;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
            return null;
        }

        public List<Cita> GetAllCites(int codiMetge)
        {
            try
            {
                using (MyDBContext context = new MyDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (DbCommand consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText = $@"select * from Cita where Cita_Metge_CodiEmpleat = @codiMetge";
                            DBUtil.crearParametre(consulta, "@codiMetge", codiMetge.ToString(), DbType.String);
                            DbDataReader resposta = consulta.ExecuteReader();

                            List<Cita> cites = new List<Cita>();
                            Cita cita = null;
                            while (resposta.Read())
                            {
                                String informe = null;
                                String nif = resposta.GetString(resposta.GetOrdinal("Cita_Persona_NIF"));
                                DateTime dataHora = resposta.GetDateTime(resposta.GetOrdinal("Cita_DataHora"));
                                if (!resposta.IsDBNull(resposta.GetOrdinal("Cita_Informe")))
                                {
                                    informe = resposta.GetString(resposta.GetOrdinal("Cita_Informe"));
                                }
                                

                                if(informe != null)
                                {
                                    cita = new Cita(codiMetge, nif, dataHora, informe);
                                }
                                else
                                {
                                    cita = new Cita(codiMetge, nif, dataHora);
                                }
                                cites.Add(cita);
                            }
                            return cites;
                        }
                    }
                }
            }
            catch (Exception)
            {
            }
            return null;
        }

        public List<Cita> GetCitesActualWeek(int codiMetge)
        {
            try
            {
                using (MyDBContext context = new MyDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (DbCommand consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText = $@"select * from Cita where Cita_Metge_CodiEmpleat = @codiMetge and YEARWEEK(Cita_DataHora) = YEARWEEK(CURDATE());";
                            DBUtil.crearParametre(consulta, "@codiMetge", codiMetge.ToString(), DbType.String);
                            DbDataReader resposta = consulta.ExecuteReader();

                            List<Cita> cites = new List<Cita>();
                            Cita cita = null;
                            while (resposta.Read())
                            {
                                String informe = null;
                                String nif = resposta.GetString(resposta.GetOrdinal("Cita_Persona_NIF"));
                                DateTime dataHora = resposta.GetDateTime(resposta.GetOrdinal("Cita_DataHora"));
                                if (!resposta.IsDBNull(resposta.GetOrdinal("Cita_Informe")))
                                {
                                    informe = resposta.GetString(resposta.GetOrdinal("Cita_Informe"));
                                }


                                if (informe != null)
                                {
                                    cita = new Cita(codiMetge, nif, dataHora, informe);
                                }
                                else
                                {
                                    cita = new Cita(codiMetge, nif, dataHora);
                                }
                                cites.Add(cita);
                            }
                            return cites;
                        }
                    }
                }
            }
            catch (Exception)
            {
            }
            return null;
        }

        public List<Cita> GetCitesPreviousWeek(int codiMetge)
        {
            try
            {
                using (MyDBContext context = new MyDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (DbCommand consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText = $@"select * from Cita where Cita_Metge_CodiEmpleat = @codiMetge and YEARWEEK(Cita_DataHora) = YEARWEEK(CURDATE() - INTERVAL 1 WEEK);";
                            DBUtil.crearParametre(consulta, "@codiMetge", codiMetge.ToString(), DbType.String);
                            DbDataReader resposta = consulta.ExecuteReader();

                            List<Cita> cites = new List<Cita>();
                            Cita cita = null;
                            while (resposta.Read())
                            {
                                String informe = null;
                                String nif = resposta.GetString(resposta.GetOrdinal("Cita_Persona_NIF"));
                                DateTime dataHora = resposta.GetDateTime(resposta.GetOrdinal("Cita_DataHora"));
                                if (!resposta.IsDBNull(resposta.GetOrdinal("Cita_Informe")))
                                {
                                    informe = resposta.GetString(resposta.GetOrdinal("Cita_Informe"));
                                }


                                if (informe != null)
                                {
                                    cita = new Cita(codiMetge, nif, dataHora, informe);
                                }
                                else
                                {
                                    cita = new Cita(codiMetge, nif, dataHora);
                                }
                                cites.Add(cita);
                            }
                            return cites;
                        }
                    }
                }
            }
            catch (Exception)
            {
            }
            return null;
        }

        public List<Cita> GetCitesNextWeek(int codiMetge)
        {
            try
            {
                using (MyDBContext context = new MyDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (DbCommand consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText = $@"select * from Cita where Cita_Metge_CodiEmpleat = @codiMetge and YEARWEEK(Cita_DataHora) = YEARWEEK(CURDATE() + INTERVAL 1 WEEK);";
                            DBUtil.crearParametre(consulta, "@codiMetge", codiMetge.ToString(), DbType.String);
                            DbDataReader resposta = consulta.ExecuteReader();

                            List<Cita> cites = new List<Cita>();
                            Cita cita = null;
                            while (resposta.Read())
                            {
                                String informe = null;
                                String nif = resposta.GetString(resposta.GetOrdinal("Cita_Persona_NIF"));
                                DateTime dataHora = resposta.GetDateTime(resposta.GetOrdinal("Cita_DataHora"));
                                if (!resposta.IsDBNull(resposta.GetOrdinal("Cita_Informe")))
                                {
                                    informe = resposta.GetString(resposta.GetOrdinal("Cita_Informe"));
                                }


                                if (informe != null)
                                {
                                    cita = new Cita(codiMetge, nif, dataHora, informe);
                                }
                                else
                                {
                                    cita = new Cita(codiMetge, nif, dataHora);
                                }
                                cites.Add(cita);
                            }
                            return cites;
                        }
                    }
                }
            }
            catch (Exception)
            {
            }
            return null;
        }

        public void UpdateInformeCita(Cita cita)
        {
            using (MyDBContext context = new MyDBContext())
            {
                using (DbConnection connection = context.Database.GetDbConnection())
                {
                    connection.Open();
                    DbTransaction transaccio = connection.BeginTransaction();

                    using (DbCommand consulta = connection.CreateCommand())
                    {
                        consulta.Transaction = transaccio;

                        DBUtil.crearParametre(consulta, "@informe", cita.Informe, DbType.String);
                        DBUtil.crearParametre(consulta, "@metge", cita.CodiMetge, DbType.Int32);
                        DBUtil.crearParametre(consulta, "@persona", cita.NIF, DbType.String);
                        DBUtil.crearParametre(consulta, "@dataHora", cita.DataHora, DbType.DateTime);

                        consulta.CommandText = "UPDATE Cita SET Cita_Informe = @informe " +
                            "WHERE Cita_Metge_CodiEmpleat = @metge and Cita_Persona_NIF = @persona and Cita_DataHora = @dataHora";

                        int numeroDeFiles = consulta.ExecuteNonQuery();
                        if (numeroDeFiles != 1)
                        {
                            transaccio.Rollback();
                        }
                        else
                        {
                            transaccio.Commit();
                        }
                    }
                }
            }
        }
    }
}
