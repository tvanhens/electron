(ns leiningen.new.electron
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def render (renderer "electron"))

(defn binary [file]
  (io/input-stream (io/resource (str/join "/" ["leiningen" "new" "electron" file]))))

(defn electron
  "FIXME: write documentation"
  [name application-name]
  (let [data {:name name
              :sanitized (name-to-path name)
              :application-name application-name}]
    (main/info "Generating fresh 'lein new' electron project.")
    (->files data
      ["package.json" (render "package.json" data)]
      ["packager.json" (render "packager.json" data)]
      ["resources/public/index.html" (render "index.html" data)]
      ["main.js" (render "main.js" data)]
      ["project.clj" (render "project.clj" data)]
      ["dev/user.clj" (render "user.clj" data)]
      ["src/{{sanitized}}/core.cljs" (render "core.cljs" data)]
      ["assets/osx/mount.icns" (binary "mount.icns")]
      ["assets/osx/installer.png" (binary "installer.png")])))
