namespace BlogAPI.Dtos
{
    public class BlogPostDto
    {
        public int Id { get; set; }
        public String UserName { get; set; }
        public String Message { get; set; }
        public DateTimeOffset PostedDateTime { get; set; }
    }
}
