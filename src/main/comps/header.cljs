(ns comps.header
  (:require
    [reagent.core :as r]
    ["antd-mobile" :as A]
    ["next/router" :default Router]))



(defn <header> [title]
  (fn []
   [:div.vw12 {:style {:height "45px"}}
    [:> A/NavBar {:mode "light"
                  :class "bb"
                  :icon (r/as-element [:> A/Icon {:type "left"}])
                  :onLeftClick #(.go js/window.history -1)}
                 title]]))
