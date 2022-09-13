<template>
  <header class="header" ref="headerEl">
    <nav>
      <ul class="nav-list">
        <li class="nav-item">
          <a href="../index.html"> 首页 </a>
        </li>
        <li class="nav-item">
          <a href="javascript:void(0);"> 博客列表 </a>
        </li>
        <li class="nav-item">
          <a href="../about.html"> 关于作者 </a>
        </li>
      </ul>
    </nav>
  </header>

  <!--  <main id="main" class="main page-list">-->
  <!--    <div class="main-inner">-->
  <!--      <ul v-if="list.length" class="note-list">-->
  <!--        <li class="list-item" v-for="item in list" :key="item.id">-->
  <!--          <a class="link" :href="`../details/${item.id}.html`" target="_blank">-->
  <!--            <span class="date">{{ item.createTime.slice(0, 10) }}</span>-->
  <!--            <span class="title">{{ item.title }}</span>-->
  <!--          </a>-->
  <!--        </li>-->
  <!--      </ul>-->
  <!--      <div v-else class="empty">-->
  <!--        暂无数据-->
  <!--      </div>-->
  <!--      &lt;!&ndash;分页&ndash;&gt;-->
  <!--      <div class="pagination">-->
  <!--        <ol class="pagination-list">-->
  <!--          <li class="item"-->
  <!--              :class="{'cur': item === pagination.page}"-->
  <!--              v-for="(item, index) in pages"-->
  <!--              :key="index"-->
  <!--              @click="changePage(item)"-->
  <!--          >{{ item }}-->
  <!--          </li>-->
  <!--        </ol>-->
  <!--      </div>-->
  <!--    </div>-->
  <!--  </main>-->
</template>
<script lang="ts">
import {defineComponent, reactive, onMounted, computed, getCurrentInstance, ref, nextTick} from "vue"

import apis from '../../api/articles'

// let headerEl = ref();
//
// onMounted(() => {
//   console.log('mounted', headerEl.value);
// })

export default defineComponent({
  setup() {

      let headerEl = ref();

    onMounted(() => {
      console.log('mounted', headerEl.value);
      // console.log(proxy.$zjj());
      })

    // const { proxy } = getCurrentInstance();

    const list = reactive<Array<any>>([]);

    const pagination = reactive({
      page: 1,
      rows: 8,
      total: 0,
    })

    const pages = computed(() => Math.ceil(pagination.total / pagination.rows))

    onMounted(async () => {
      await fetchList();
    })

    async function fetchList() {
      const articleRes: any = await apis.fetchArticleList({
        page: pagination.page,
        rows: pagination.rows,
      })

      pagination.total = articleRes.total;
      list.splice(0, list.length, ...articleRes.items);
    }

    function changePage(num: number) {
      pagination.page = num;
      fetchList();
    }

    return {
      list,
      pagination,
      pages,
      changePage,
      headerEl
    }
  }
})
</script>

<style lang="scss">

.header {
  background: #373d49;
}

.nav-list {
  max-width: 688px;
  margin: 0 auto;
  display: flex;
  height: 88px;
  padding: 0 5px;
}

.nav-item {
  padding: 0 15px;
  line-height: 88px;
  transition: background 0.35s;

  &:hover a {
    //background: orange;
    color: $main-color;
  }

  a {
    text-decoration: underline;
    color: #fff;
  }
}

.page-list {
  max-width: 688px;
  margin: 0 auto;
  padding-top: 8px;

  .loading {
    text-align: center;
  }

  .date {
    color: #999;
  }
}

.note-list {
  .list-item {
    padding: 30px 0 20px;
    border-bottom: 1px dashed #999;;

    .link {
      display: flex;
      font-size: 16px;
      line-height: 20px;
      text-decoration: none;

      &:hover {
        .title {
          color: $main-color;
        }
      }

      .title {
        color: #333;
        font-size: 20px;
        transition: color 0.2s ease;
      }

      .date {
        width: 100px;
        font-size: 13px;
      }
    }
  }
}

.empty {
  text-align: center;
  color: #333;
  line-height: 88px;
}

.pagination {
  padding-top: 40px;
  width: 100%;
  /*text-align: center;*/

  .pagination-list {
    padding: 0;

    .item {
      display: inline-block;
      width: 30px;
      height: 30px;
      margin: 0 3px;
      cursor: pointer;
      text-align: center;
      line-height: 30px;
      background-color: #333;

      &.cur {
        color: $main-color;
        background-color: #444;
      }

      &:hover {
        color: $main-color;
        background-color: #555;
      }
    }
  }
}

.icp {
  display: block;
  color: #333;
  text-align: center;
  margin-bottom: 20px;
}
</style>