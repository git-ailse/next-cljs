(ns pages.msg
  (:require
    [reagent.core :as r]
    [shadow.next-js :as sn]
    ["next/link" :default Link]))

(defn main
  {:export true
   :next/page "msg"}
  [props]
  (r/as-element
    [:div
     [:h1 "Hi msg!"]

     [:p "Welcome to your new next.js site."]
     [:p "Now go build something great with ClojureScript."]

     [:> Link {:href "/page-2"} [:a "goto page-2"]]]))
