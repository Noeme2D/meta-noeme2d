SUMMARY = "C from Python test"
DESCRIPTION = "Passes memory in and out Python and loads cv2 and onnxruntime."
LICENSE = "CLOSED"

SRC_URI += "file://Makefile"
SRC_URI += "file://inc/"
SRC_URI += "file://src/"

DEPENDS += "python3-numpy opencv onnxruntime"
inherit python3-dir pkgconfig

S = "${WORKDIR}"
EXTRA_OEMAKE = " \
    'CC=${CC}' 'RANLIB=${RANLIB}' 'AR=${AR}' 'CFLAGS=${CFLAGS}' 'BUILDDIR=${S}' \
    'Python_NumPy_INCLUDE_DIR'=${STAGING_LIBDIR}/${PYTHON_DIR}/site-packages/numpy/core/include \
"

EXE = "c-py-test"

do_configure() {
}

do_compile() {
    oe_runmake ${EXE}
}

do_install() {
    mkdir -p ${D}${bindir}
    install -m 0755 ${EXE} ${D}${bindir}
}
FILES:${PN} = "${bindir}"

do_clean() {
    oe_runmake clean-${EXE}
}