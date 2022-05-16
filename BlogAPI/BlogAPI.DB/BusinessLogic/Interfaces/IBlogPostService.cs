namespace BlogAPI.lib.BusinessLogic.Interfaces
{
    public interface IBlogPostService
    {
        Task<List<BlogPost>> GetBlogPosts();
        Task PostBlogPosts(BlogPost blogPost);
    }
}