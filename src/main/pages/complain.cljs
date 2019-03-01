(ns pages.complain
  (:require
    [reagent.core :as r]
    [shadow.next-js :as sn]
    ["next/link" :default Link]
    ["antd-mobile" :as A]
    ["axios" :as http]
    [comps.banner_header :refer [<banner_header>]]
    [comps.header :refer [<header>]]))

(declare <form-section>)


(defn main
  {:export true
   :next/page "complain"}
  [props]
  (prn props)
  (r/as-element
    [:div.flex.column.vh100
     [<header> "投诉中心"]
     [<banner_header>]
     [:div.flex1.bg-white
      [:div.px3.py2 "有任何投诉事宜,请随时拨打"]
      [:a.primary.f1.tc.block {:href "tel:4008005588" :style {:padding "10px 0"}} "4008005588"]
      [<form-section>]]]))


;ui----------
(defn <form-section>
  []
  [:section.py2
   [:div.px3 "如果觉得方便,您也可以通过以下表单在线提交:"]
   [:div.py2.px3.css "投诉内容:"]
   [:> A/TextareaItem {:class "b mx3"  :rows "{5}" :count "{100}"}]
   [:> A/List {:class "py2"}
    [:> A/Picker {:value "" :arrow "horizontal"}
     [:> A/List.Item "投诉科室:"]]
    [:> A/Picker {:value "" :arrow "horizontal"}
     [:> A/List.Item "投诉医生:"]]
    [:> A/InputItem  {:placeholder "(选填)"} "称呼:"]]])
