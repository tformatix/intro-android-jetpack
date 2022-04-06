using BlogAPI.lib.BusinessLogic.Interfaces;
using BlogAPI.lib.Database;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlogAPI.lib.BusinessLogic.Implementations
{
    public class BlogPostService : IBlogPostService
    {
        private readonly BlogContext _context;

        public BlogPostService(BlogContext context)
        {
            _context = context;
        }

        public async Task<List<BlogPost>> GetBlogPosts()
        {
            return await _context.BlogPost
                .ToListAsync();
        }

        public async Task PostBlogPosts(BlogPost blogPost)
        {
            await _context.BlogPost
                .AddAsync(blogPost);
            await _context.SaveChangesAsync();
        }
    }
}
