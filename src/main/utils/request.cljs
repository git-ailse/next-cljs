(ns utils.request
  (:require
    ["superagent" :as superagent]
    ["superagent-use" :as superagent-use]
    ["override-fn" :as override-fn!]
    ["window-or-global" :as root]
    [utils.const :as const]
    [oops.core :refer [oset!]]))

(prn (.-jwt root))

;; 拦截器
(defn- interceptor
  [request]
  (let [url (.-url request)
        not-complete-url? (not (re-find #"^http" url))
        method (.-method request)]
    ;; 补全url
    (if not-complete-url?

      (oset! request "url" (str const/REST_URL url)))

    ;; headers参数转换（通过改写request.set函数实现）
    (override-fn! request "set"
                  (fn [base-fn field val]
                    (base-fn (clj->js field :keywordize-keys true) (clj->js val :keywordize-keys true))))

    (.set request "Authorization" (str "Bearer" (.-jwt root)))
    ;; query参数转换（通过改写request.query函数实现）
    (override-fn! request "query" (fn [base-fn data] (base-fn (clj->js data :keywordize-keys true))))
    ;; post body转换（通过重载request.send函数实现, 注: 直接改request._data无效）
    (if (re-find #"(POST|PATCH|PUT)" method)
      (do (override-fn! request "send" (fn [base-fn data] (base-fn (clj->js data :keywordize-keys true))))
          (.set request "Prefer" "return=representation")))
    ;; request发出事件回调, 注: 此处无法改变请求的内容
    (.on request "request"
      (fn [request]))
        ; (js/console.log "request" request)))

    ;; response收到事件回调
    (.on request "response"
      (fn [response]
        ; (js/console.log "response" response)
        ;; 将body转为clj格式，注整个response仍是js对象
        (oset! response "body" (js->clj (.-body response) :keywordize-keys true))))))


;; export
(def request ;注：此request不同于拦截器的request参数
  (-> superagent (superagent-use) (.use interceptor)))
