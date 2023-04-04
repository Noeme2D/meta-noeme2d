SUMMARY = "Test GL context draw"
DESCRIPTION = "Drawings expected to be displayed."
LICENSE = "CLOSED"

SRC_URI += "file://Makefile"
SRC_URI += "file://inc/"
SRC_URI += "file://src/"

DEPENDS += "virtual/egl virtual/libgles2"
DEPENDS += "patchelf-native"
inherit pkgconfig

S = "${WORKDIR}"
EXTRA_OEMAKE = "'CC=${CC}' 'RANLIB=${RANLIB}' 'AR=${AR}' 'CFLAGS=${CFLAGS}' 'BUILDDIR=${S}'"

EXE = "gl-test"

do_configure() {
}

do_compile() {
    oe_runmake ${EXE}
}

do_install() {
    # Dynamic link to Mesa drivers instead of proprietary ones
    patchelf --replace-needed libEGL.so libEGL.so.1 ${EXE}
    patchelf --replace-needed libGLESv2.so libGLESv2.so.2 ${EXE}

    mkdir -p ${D}${bindir}
    install -m 0755 ${EXE} ${D}${bindir}
}
FILES:${PN} = "${bindir}"

do_clean() {
    rm -f ${EXE}
}