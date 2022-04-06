using BlogAPI.Dtos;
using BlogAPI.lib;
using BlogAPI.lib.BusinessLogic.Interfaces;
using BlogAPI.lib.Utils;
using BlogAPI.Models;
using Microsoft.AspNetCore.Mvc;

namespace BlogAPI.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class BlogPostController : ControllerBase
    {
        private readonly IBlogPostService _blogService;

        public BlogPostController(IBlogPostService blogService)
        {
            _blogService = blogService;
        }

        [HttpGet]
        [ProducesResponseType(typeof(List<BlogPostDto>), 200)]
        [ProducesResponseType(typeof(String), 500)]
        public async Task<IActionResult> GetAll()
        {
            try
            {
                var blogPosts = await _blogService.GetBlogPosts();
                return Ok(blogPosts.Select(x =>
                    x.CopyPropertiesTo(new BlogPostDto())
                ).ToList());
            }
            catch (Exception ex)
            {
                return StatusCode(500, ex.Message);
            }
        }

        [HttpPost]
        [ProducesResponseType(200)]
        [ProducesResponseType(typeof(String), 500)]
        public async Task<IActionResult> Post([FromBody] BlogPostModel blogPostModel)
        {
            try
            {
                var blogPost = blogPostModel.CopyPropertiesTo(new BlogPost());
                await _blogService.PostBlogPosts(blogPost);
                return Ok();
            }
            catch (Exception ex)
            {
                return StatusCode(500, ex.Message);
            }
        }
    }
}