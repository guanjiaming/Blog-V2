import Profile from "../pages/Profile/Profile";
import Articles from "../pages/Articles/Articles";
import Article from "../pages/Article/Article";
import Tags from "../pages/Tags/Tags";
import Images from "../pages/Images/Images";
import Login from "../pages/Login/Login";
import NotFound from "../pages/404";

const router: Array<any> = [
    {path: "/", name: "Profile", component: Profile, auth: true},
    {path: "/profile", name: "Profile", component: Profile, auth: true},
    {path: "/articles", name: "Articles", component: Articles, auth: true},
    {path: "/article/:id", name: "Article", exact: true, component: Article, auth: true},
    {path: "/tags", name: "Tags", component: Tags, auth: true},
    {path: "/images", name: "Images", component: Images},
    {path: "/login", name: "Login", component: Login},
    {path: "/404", name: "NotFound", component: NotFound},
]

export default router;