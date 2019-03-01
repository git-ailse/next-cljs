(ns pages.plan
  (:require
    [reagent.core :as r]
    [shadow.next-js :as sn]
    ["next/link" :default Link]
    ["axios" :as http]
    ["antd-mobile" :as A]
    [comps.tips :refer [<tips>]]
    [comps.banner_header :refer [<banner_header>]]
    [comps.header :refer [<header>]]))

(defn plan
  {:export true
   :next/page "plan"}
  [props]
  (prn props)
  (r/as-element
    [:div.flex.column.vh100
     [<header> "随访计划"]
     [<banner_header>]
     [:div.flex1.bg-white
      [:div.py2.px3.bb "最近随访"]
      (doall
        (for [index (range 1)]
         ^{:key index}
          [:div.py2.px3.bb.flex.align-center
           [:div.flex1
            [:div.f3 "ItmeName"]
            [:div.f4 "随访日期:" "2018-10-22"]]
           [:> A/Button { :size "small" :type "primary"} "参与"]]))
      [<tips> "萧江平"]]]))
