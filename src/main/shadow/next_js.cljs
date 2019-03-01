(ns shadow.next-js
  (:require [goog.object :as gobj]))

(defn add-query [page-fn query-fn]
  (gobj/set page-fn
            "getInitialProps"
            (fn [ctx]
                (-> (query-fn (js->clj ctx :keywordize-keys true))
                    (.then (fn [res]
                             (if (vector? res)
                               (clj->js (map #(.-body %) res))
                               (clj->js (.-body res)))))))))
