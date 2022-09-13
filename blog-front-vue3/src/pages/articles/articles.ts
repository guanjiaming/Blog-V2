/* Created by guanjiaming on 2022/01/06 */
import { createApp } from 'vue'
import App from './articles.vue'
const app = createApp(App);
app.config.globalProperties.$zjj = function (){
    // alert("iloveyou");
}

app.mount('#app');
