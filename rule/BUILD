load(":foo.bzl", "foo_binary", "hello_world")
exports_files(["file.cc.tpl"])
foo_binary( name = "bin",
	   username ="Alice" )


hello_world(
	name = "hello",
	username = "Alice",
	#template = "file.cc.tpl",
)


hello_world(
	name = "hello_bin",
	username = "Alice",
	#template = "file.cc.tpl",
)