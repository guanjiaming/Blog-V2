/* Created by guanjiaming on 2021/12/17 */
import request from "./request";

class BlogService {


  fetchArticleList(data: any) {
    return request.get({
      url: "/apis/queryArticleList",
      data
    })
  }
}

export default new BlogService()
