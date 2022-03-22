using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlogAPI.lib
{
    public class BlogPost
    {
        public int Id { get; set; }
        public String UserName { get; set; }
        public String Message { get; set; }
        public DateTimeOffset PostedDateTime { get; set; } = DateTimeOffset.Now;
    }
}
