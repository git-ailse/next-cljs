(ns pages.return_mention
  (:require
    [reagent.core :as r]
    [shadow.next-js :as sn]
    ["next/link" :default Link]
    ["axios" :as http]
    ["antd-mobile" :as A]
    [comps.tips :refer [<tips>]]
    [comps.banner_header :refer [<banner_header>]]
    [comps.header :refer [<header>]]))

(defn return_mention
  {:export true
   :next/page "return_mention"}
  [props]
  (prn props)
  (r/as-element
    [:div.flex.column.vh100
     [<header> "复诊提醒"]
     [<banner_header>]
     [:div.flex1.bg-white
      [:div.py2.px3.bb "最近提醒"]
      (doall
        (for [index (range 10)]
         ^{:key index}
          [:div.py2.px3.bb.flex.align-center
           [:div.flex1
            [:div.f4 "2018-10-22"]
            [:div.ml2.f3 "ItmeName"]]
           [:> A/Button { :size "small" :type "primary"} "反馈"]]))
      [<tips> "萧江平"]]]))
