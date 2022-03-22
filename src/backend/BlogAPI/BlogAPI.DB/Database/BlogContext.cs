using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlogAPI.lib.Database
{
    public class BlogContext: DbContext
    {
        public BlogContext() { }

        public BlogContext(DbContextOptions<BlogContext> options) : base(options) { }

        public virtual DbSet<BlogPost> BlogPost { get; set; }
    }
}
