(ns pages.contact
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
    [shadow.next-js :as sn]
    [reagent.core :as r]
    [comps.tabs :refer [<tabs>]]
    [utils.request :refer [request]]
    ["antd" :as A]
    ["next/link" :default Link]))


; state
; ----------------------------------------------
(defonce st-users (r/atom [])) ;用户
(defonce st-menus (r/atom [])) ;菜单数据
(defonce st-collapsed (r/atom false)) ;是否收缩


; action
; ----------------------------------------------
; 切换菜单收缩/展开
(defn do-toggle-collapsed
  []
  (swap! st-collapsed not))

; 获取用户
(defn do-get-users
  [query callback]
  (-> request
      (.get "user")
      (.set "Accept" "application/json")
      (.query query)
      (.query "limit=1")
      (.query {:select "id,name,hos(name)"
               :order "id.desc"})
      (.then #(callback (.-body %)))))

; 更新用户
(defn do-patch-users
  []
  (-> request
      (.patch "user")
      ; (.set "Prefer" "return=representation")
      (.query {:id "eq.3"})
      (.send {:name "前台6"})
      (.then #(prn (.-body %)))))


; ui
; ----------------------------------------------
; 切换菜单收缩/展开按钮
(defn <menu-collapse-button>
  []
  [:> A/Button {:onClick do-toggle-collapsed
                :style {:marginBottom 20}}
               [:> A/Icon {:type (if @st-collapsed "menu-unfold" "menu-fold")}]])

; 菜单组（含子菜单）
(defn <menu-group>
  []
  (let [SubMenu (.-SubMenu A/Menu)]
    [:> A/Menu {:onClick #()
                :style {:maxWidth 256}
                :theme "dark"
                :mode "inline"
                :inlineCollapsed @st-collapsed
                :defaultSelectedKeys ["1"]
                :defaultOpenKeys ["sub1"]}
               [:> A/Menu.Item {:key "0"}
                               [:> A/Icon {:type "pie-chart"}]
                               [:span "option 0"]]
               [:> SubMenu {:key "sub1"
                            :title (r/as-element [:span
                                                  [:> A/Icon {:type "appstore"}]
                                                  [:span "sub1"]])}
                           [:> A/Menu.Item {:key "1"} "option 1"]
                           [:> A/Menu.Item {:key "2"} "option 2"]]
               [:> SubMenu {:key "sub2"
                            :title (r/as-element [:span
                                                  [:> A/Icon {:type "mail"}]
                                                  [:span "sub1"]])}
                           [:> A/Menu.Item {:key "3"} "option 3"]
                           [:> A/Menu.Item {:key "4"} "option 4"]]]))

; 列表项
(defn <item>
  [idx user]
  [:div.m2.p2.primary.bb {:key idx}
   [:span (str idx " " (:name user))]])

; 列表
(defn <list>
  []
  [:div.m2
   [:div.f1 "users"]
   (case @st-users
     []  [:div "加载中..."]
     nil [:div "暂无内容"]
         [:div (map-indexed #(<item> %1 %2) @st-users)])])


; page
; ----------------------------------------------
(defn main
  {:export true
   :next/page "contact"}
  [props]
  ; (js/console.log (.-users props)) ;;打印json
  (prn (js->clj (.-users props) :keywordize-keys true)) ;;打印edn
  (reset! st-users (js->clj (.-users props) :keywordize-keys true))
  (r/as-element
    [:div
     [:div.main
      [:div
       [:h1 {:onClick do-patch-users} "Hi contact2!"]
       [<list>]
       [<menu-collapse-button>]
       [<menu-group>]
       [:div.mt2
        [:a {:href "/"} "go to the home page1"]]]]
     [:div.footer
      [<tabs> 1]]]))

(defn init-props
  [req]
  (let [query (.-query req)]
       (do-get-users query #(hash-map :users %))))

(sn/add-query main init-props)
