(ns pages.feedback
  (:require
    [reagent.core :as r]
    [shadow.next-js :as sn]
    ["next/link" :default Link]
    ["axios" :as http]
    [comps.header :refer [<header>]]))

(defn feedback
  {:export true
   :next/page "feedback"}
  [props]
  (prn props)
  (r/as-element
    [:div
     [<header> "意见建议"]]))
