SUMMARY = "List OpenGL Extensions"
DESCRIPTION = "Print all supported OpenGL extensions onboard."
LICENSE = "CLOSED"

SRC_URI += "file://Makefile"
SRC_URI += "file://inc/"
SRC_URI += "file://src/"

DEPENDS += "virtual/egl virtual/libgles2"
DEPENDS += "patchelf-native"
inherit pkgconfig

S = "${WORKDIR}"
EXTRA_OEMAKE = "'CC=${CC}' 'RANLIB=${RANLIB}' 'AR=${AR}' 'CFLAGS=${CFLAGS}' 'BUILDDIR=${S}'"

do_configure() {
}

do_compile() {
    oe_runmake ls-gl-ext
}

do_install() {
    # Dynamic link to Mesa drivers instead of proprietary ones
    patchelf --replace-needed libEGL.so libEGL.so.1 ls-gl-ext
    patchelf --replace-needed libGLESv2.so libGLESv2.so.2 ls-gl-ext

    mkdir -p ${D}${bindir}
    install -m 0755 ls-gl-ext ${D}${bindir}
}
FILES:${PN} = "${bindir}"

do_clean() {
    rm -f ls-gl-ext
}