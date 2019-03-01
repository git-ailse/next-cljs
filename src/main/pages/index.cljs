(ns pages.index
  (:require
    [reagent.core :as r]
    [shadow.next-js :as sn]
    ["antd-mobile" :as A]
    ["next/link" :default Link]
    ["next/router" :default Router]
    [comps.banner_header :refer [<banner_header>]]))


(def items [{:icon "/static/remind.png" :text "复诊提醒" :desc "复诊提醒在线查看" :path "/return_mention"}
            {:icon "/static/plan.png" :text "随访计划" :desc "我的随访计划" :path "/plan"}
            {:icon "/static/health.png" :text "健康定制" :desc "在线定制服务"}
            {:icon "/static/feedback.png" :text "投诉中心" :desc "在线投诉" :path "/complain"}
            {:icon "/static/integral.png" :text "会员积分" :desc "复诊提醒在线查看"}
            {:icon "/static/suggest.png" :text "意见建议" :desc "我要提意见建议" :path "/feedback"}])

(declare <up-section> <down-section>)

(defn main
  {:export true
   :next/page "index"}
  [props]
  (prn props)
  (r/as-element
    [:div.flex.column.vh100
     [<banner_header>]
     [<up-section>]
     [<down-section>]]))

; ui
; ----------------------------------------------
(defn <up-section>
  []
  [:div.flex.align-center.bg-white
   [:div.w5.tc.br {:style {:padding "30px 0"} :onClick #(.push Router "/pat_info")}
    [:img.block.mx-auto {:src "/static/my.png" :style {:width "30px"}}]
    [:div.mt1 "我的信息"]
    [:div.gray.block "个人信息展示"]]
   [:div.w7 {:style {:min-width "200px"}}
    (let [items [{:icon "/static/consult.png" :text "咨询大厅" :desc "在线咨询,快速解答" :path "/medical_record"}
                 {:icon "/static/record.png" :text "诊疗记录" :desc "查看、上传诊疗记录" :path "/medical_record"}]]
      (doall
        (for [index (range (count items))]
         (let [{:keys [icon text desc path] :as all } (get items index)]
           ^{:key index}
           [:div.flex.py2.align-center {:class (if (>  index 0) "bt") :onClick #(.push Router path)}
            [:img.mx3 {:src icon :style {:width "30px" :height "30px"}}]
            [:div.flex1
             [:div text]
             [:div.gray desc]]]))))]])


(defn <down-section>
  []
  [:div.mt3.bg-white.flex1
   [:div.flex.wrap
    (doall
      (for [index (range (count items))]
       (let [{:keys [icon path text desc] :as all } (get items index)]
         ^{:key index}
         [:div.w6 {:class (str (if (> (rem index 2) 0 ) "bl") " " (if (> index 1) "bt"))
                   :onClick #(if(= path nil) ( (.. A -Toast -info) "正在努力建设中") (.push Router path))}
          [:div.flex.py2.align-center
           [:img.mx3 {:src icon :style {:width "30px" :height "30px"}}]
           [:div.flex1
            [:div text]
            [:div.gray desc]]]])))]])
