(ns comps.tabs
  (:require
    [reagent.core :as r]
    ["antd-mobile" :as A]
    ["next/router" :default Router]))

(def st-tabs [{:icon "https://www.baidu.com/img/bd_logo1.png" :path "/"}
              {:icon "https://www.baidu.com/img/bd_logo1.png" :path "/contact?id=eq.3"}
              {:icon "https://www.baidu.com/img/bd_logo1.png" :path "/my?id=eq.3"}])


(defn <tabs> [idx]
  (fn []
   [:> A/TabBar {:unselectedTintColor "#949494"
                 :tintColor "#33A3F4"
                 :barTintColor "white"}
                (doall
                  (for [index (range (count st-tabs))]
                   (let [{:keys [icon path] :as all } (get st-tabs index)]
                    ; (prn icon (vals all))
                    ^{:key index} [:> A/TabBar.Item {:icon {:uri icon}
                                                     :selectedIcon {:uri icon}
                                                     :selected (= idx index)
                                                     :title index
                                                     :onPress #(and (not= idx index) (.push Router path))}])))]))
