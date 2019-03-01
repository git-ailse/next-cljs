(ns pages.record_upload
  (:require
    [reagent.core :as r]
    [shadow.next-js :as sn]
    ["antd-mobile" :as A]
    [utils.request :refer [request]]
    [promesa.core :as p]
    [comps.header :refer [<header>]]
    ["next/link" :default Link]))


(declare <picker>)

; 可变状态命名前加*号为了区分可变
(def *dateValue  (r/atom (js/Date.)))
(def *selectDept  (r/atom 0))
(def *selectDoc  (r/atom 0))
; (add-watch *selectDoc nil #(prn %4))

;-----------do
; getDepts
(defn get-depts
  [ctx]
  (-> request
      (.get "dept")
      (.query {:select "label:name, value:id"})))
;getDocs
(defn get-docs
  [ctx]
  (-> request
      (.get "doc")
      (.query {:select "label:name, value:id"})))


(defn main
  {:export true
   :next/page "record_upload"}
  [props]
  (prn props)
  (let [{:keys [data userInfo]} (js->clj props :keywordize-keys true)
        [depts docs] data]
    (r/as-element
     [:div.flex.column.vh100
      ; (prn depts)
      [<header> "上传诊疗记录"]
      [:div.flex1.bg-white
       [:div.p3 "通过自助上传诊疗记录，您可以获得复诊提醒，医生随访等个性化服务。"]
       [<picker> depts docs]]])))

; ui
; ----------------------------------------------
(defn <picker>
  [depts docs]
  [:div
    [:> A/List
     [:> A/Picker {:data (or depts []) :cols 1 :value @*selectDept :arrow "horizontal" :onOk #(reset! *selectDept %)}
      [:> A/List.Item "科室"]]
     [:> A/Picker {:value ""}
      [:> A/List.Item "治疗项目"]]
     [:> A/Picker {:data (or docs []) :cols 1 :value @*selectDoc :onOk #(reset! *selectDoc %)}
      [:> A/List.Item "就诊医生"]]
     [:> A/DatePicker {:value @*dateValue
                       :mode "date"
                       :extra "请选择"
                       :arrow "horizontal"
                       :onChange #(reset! *dateValue %)}
      [:> A/List.Item "完成日期"]]]
    [:> A/Button {:class "vw10 mx-auto my4" :type "primary" :onClick #() } "确定"]])


(sn/add-query main #(p/all [(get-depts %) (get-docs %)]))
