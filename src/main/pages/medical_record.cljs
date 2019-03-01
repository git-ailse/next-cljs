(ns pages.medical_record
  (:require
    [reagent.core :as r]
    [clojure.string :as String]
    [shadow.next-js :as sn]
    ["next/link" :default Link]
    ["axios" :as http]
    ["antd-mobile" :as A]
    ["next/router" :default Router]
    ["dayjs" :as dayjs]
    [utils.request :refer [request]]
    [promesa.core :as p]
    [promesa.async-cljs :refer-macros [async]]
    [comps.header :refer [<header>]]
    [comps.tips :refer [<tips>]]
    [comps.banner_header :refer [<banner_header>]]))


;-----------st


;;-----------do
;; 获取诊疗记录
(defn get-medical-records
  [ctx]
  (-> request
      (.get "emr")
      (.query {:pat_id (str "eq.2") :limit "3"}) ;(-> ctx :userInfo :user_id))})
      (.query {:select "*"})))
               ; id, types, created_at, updated_at, is_finished, finished_time,medical_item(id, name, types, created_at, updated_at, dept(id, name), doc(id, name))})))


;-------------ui
; 最近记录
(defn <medical_records>
  [userInfo medical_records]
  (let []
    [:div.flex1.bg-white
     [:div.py2.px3.bb.flex
      [:div.flex1 "最近记录"]
      [:div.primary "更多"]]
     (for [record medical_records]
       (let [{:keys [id ill_start_date advice main_say]} record]
     ;                {:keys [name]} :medical_item} medical_record]
         ^{:key id}
         [:div.py2.px3.bb
          [:div.f4 ill_start_date]
               ; (str (if is_finished "[已完成]" "[未完成]")
                    ; (-> finished_time (or updated_at) (dayjs) (.format "MM月DD日HH:MM"))]
          [:div.ml2.f3 (or advice main_say)]]))
     [:> A/Button {:class "vw10 mx-auto my4" :type "primary" :onClick #(.push Router "/record_upload") } "立即添加"]
     [<tips> (:name userInfo)]]))


;------------main
(defn main
  {:export true
   :next/page "medical_record"}
  [props]
  (let [{:keys [userInfo data]} (js->clj props :keywordize-keys true)]
    (r/as-element
      [:div.flex.column.vh100
       [<header> "诊疗记录"]
       [<banner_header>]
       [<medical_records> userInfo data]])))

(sn/add-query main get-medical-records)
