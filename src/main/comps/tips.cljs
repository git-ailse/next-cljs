(ns comps.tips
  (:require
    [reagent.core :as r]
    ["antd-mobile" :as A]
    ["next/router" :default Router]))



(defn <tips> [userName]
 (fn []
  [:div.p3
   [:div
    [:span.f1.red.mr2.iconfont.icon-xianshi_tishi {:style {:vertical-align "middle"}}]
    "小提示"]
   [:div "您已绑定就诊人信息"
    [:span.primary userName]
    ",系统将及时同步您的诊疗记录."]]))
