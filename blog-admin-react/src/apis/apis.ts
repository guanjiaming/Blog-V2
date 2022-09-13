import request from "../utils/request";

class Service {

    login(data: any) {
        return request({
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            method: "POST",
            url: "/login",
            data: data
        })
    }

    /**
     * 查询文章列表
     * @param data
     */
    fetchArticleListPage(data: any) {
        return request({
            method: "GET",
            url: "/queryArticleList",
            params: data
        })
    }

    /**
     * 保存文章
     */
    fetchSaveArticle(data: Article) {
        return request({
            method: "POST",
            url: "/article/save",
            data
        })
    }

    /**
     * 修改文章
     */
    fetchEditArticle(data: Article) {
        return request({
            method: "PUT",
            url: "/article/edit",
            data
        })
    }

    /**
     * 删除文章
     */
    fetchDeleteArticle(id: number) {
        return request({
            method: "DELETE",
            url: `/article/delete/${id}`
        })
    }

    /**
     * 查询文章详情
     * @param articleId
     */
    fetchArticleDetail(articleId: string) {
        return request({
            method: "GET",
            url: `/article/detail/${articleId}`,
        })
    }

    /**
     * 查询所有标签
     */
    fetchTagList() {
        return request({
            method: "GET",
            url: "/tags"
        })
    }

    /**
     * 搜索标签
     */
    fetchTagByName(name: string) {
        return request({
            method: "GET",
            url: "/tag",
            params: {name}
        })
    }

    /**
     * 新增标签
     * @param data
     */
    fetchSaveTag(data: Tag) {
        return request({
            method: "POST",
            url: "/tag",
            data
        })
    }

    /**
     * 修改标签
     * @param data
     */
    fetchUpdateTag(data: Tag) {
        return request({
            method: "PUT",
            url: "/tag",
            data
        })
    }

    /**
     * 删除标签
     * @param id
     */
    fetchDeleteTag(id: number) {
        return request({
            method: "DELETE",
            url: `/tag/${id}`
        })
    }

    /**
     * 获取图片素材列表
     */
    fetchPictureListPage(name: string) {
        return request({
            method: "GET",
            url: "/tag",
            params: {name}
        })
    }
}

interface Tag {
    id: number | null
    name: string

}

interface Article {
    id?: string
    title: string
    content: string
    tagIds: number[]
    isRecommend: boolean
    isPublish: boolean
}

export default new Service();