using Microsoft.EntityFrameworkCore;

namespace GestioInformesHorariBD.BDContext
{
    public class MyDBContext : DbContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder optionBuilder)
        {
            optionBuilder.UseMySQL("Server=51.68.224.27;Database=dam2_fperez2;UID=dam2_fperez2;Password=1025A;SslMode=none");
        }
    }
}
