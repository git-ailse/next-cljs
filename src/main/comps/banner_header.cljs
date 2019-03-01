(ns comps.banner_header
  (:require
    [reagent.core :as r]
    ["antd-mobile" :as A]
    ["next/router" :default Router]))



(defn <banner_header> []
 (fn []
   [:img.vw12 {:src "http://img.diandianys.com/ghbg2.png"}]))
