(ns pages.pat_info
  (:require
    [reagent.core :as r]
    [shadow.next-js :as sn]
    ["next/link" :default Link]
    [utils.request :refer [request]]
    [comps.header :refer [<header>]]))

(defn pat_info
  {:export true
   :next/page "pat_info"}
  [props]
  (prn props)
  (r/as-element
    [:div.flex.column.vh100
     [<header> "绑定就诊人信息"]
     ; 个人信息
     [:div.flex1.bg-white
      [:div.relative
       [:img.w12 {:src "./static/user-bg.png"}]
       [:div.absolute.tc.vw12 {:style {:top "20px"}}
        [:div.div-avatar.lg-avatar.mx-auto.circle {:style {:background-image (js* "`url(${props.userInfo.avatar || './static/my.png'}  )`")}}]
        [:div.mt1 "欢迎! " (.. props -userInfo -name)]]]
      ; 内容
      [:div.py2.px3
       [:div.tc.my2 "您已绑定就诊人信息"]
       [:div.my2.bold "绑定就诊人信息能干嘛"]
       [:div
        [:div "查看就诊记录"]
        [:div "快速咨询医生"]
        [:div "与就诊过的医生直接沟通"]
        [:div "接收复诊提醒"]
        [:div "接收随访提醒"]]]]]))
