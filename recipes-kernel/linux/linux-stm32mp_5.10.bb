SUMMARY = "Linux STM32MP Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
#LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

include linux-stm32mp.inc

LINUX_VERSION = "5.10"
LINUX_SUBVERSION = "188"
LINUX_TARNAME = "linux-${LINUX_VERSION}.${LINUX_SUBVERSION}"
SRC_URI = "https://cdn.kernel.org/pub/linux/kernel/v5.x/${LINUX_TARNAME}.tar.xz;name=kernel"
#SRC_URI = "https://git.kernel.org/torvalds/t/linux-${LINUX_VERSION}-${LINUX_SUBVERSION}.tar.gz;name=kernel"

SRC_URI[kernel.sha256sum] = "c9558bab35e23ae67661bfb3192c609c857f78582a035449ae63e33d04ab6112"

SRC_URI += " \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0001-ARM-5.10.188-stm32mp1-r2.3-rc1-MACHINE.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0002-ARM-5.10.188-stm32mp1-r2.3-rc1-CLOCK.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0003-ARM-5.10.188-stm32mp1-r2.3-rc1-CPUFREQ.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0004-ARM-5.10.188-stm32mp1-r2.3-rc1-CPUIDLE-POWER.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0005-ARM-5.10.188-stm32mp1-r2.3-rc1-CRYPTO.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0006-ARM-5.10.188-stm32mp1-r2.3-rc1-DMA.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0007-ARM-5.10.188-stm32mp1-r2.3-rc1-DRM.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0008-ARM-5.10.188-stm32mp1-r2.3-rc1-HWSPINLOCK.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0009-ARM-5.10.188-stm32mp1-r2.3-rc1-I2C-IIO-IRQCHIP.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0010-ARM-5.10.188-stm32mp1-r2.3-rc1-MAILBOX-REMOTEPROC-RPMSG.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0011-ARM-5.10.188-stm32mp1-r2.3-rc1-MEDIA-SOC-THERMAL.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0012-ARM-5.10.188-stm32mp1-r2.3-rc1-MFD.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0013-ARM-5.10.188-stm32mp1-r2.3-rc1-MMC.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0014-ARM-5.10.188-stm32mp1-r2.3-rc1-NET-TTY.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0015-ARM-5.10.188-stm32mp1-r2.3-rc1-PERF.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0016-ARM-5.10.188-stm32mp1-r2.3-rc1-PHY-USB.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0017-ARM-5.10.188-stm32mp1-r2.3-rc1-PINCTRL-REGULATOR-SPI.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0018-ARM-5.10.188-stm32mp1-r2.3-rc1-RESET-RTC-WATCHDOG.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0019-ARM-5.10.188-stm32mp1-r2.3-rc1-SCMI.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0020-ARM-5.10.188-stm32mp1-r2.3-rc1-SOUND.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0021-ARM-5.10.188-stm32mp1-r2.3-rc1-MISC.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0022-ARM-5.10.188-stm32mp1-r2.3-rc1-DEVICETREE.patch \
    file://${LINUX_VERSION}/${LINUX_VERSION}.${LINUX_SUBVERSION}/0023-ARM-5.10.188-stm32mp1-r2.3-rc1-CONFIG.patch \
    "

LINUX_TARGET = "stm32mp"
LINUX_RELEASE = "r2.3"

PV = "${LINUX_VERSION}.${LINUX_SUBVERSION}-${LINUX_TARGET}-${LINUX_RELEASE}"

ARCHIVER_ST_BRANCH = "v${LINUX_VERSION}-${LINUX_TARGET}"
ARCHIVER_ST_REVISION = "v${LINUX_VERSION}-${LINUX_TARGET}-${LINUX_RELEASE}"
ARCHIVER_COMMUNITY_BRANCH = "linux-${LINUX_VERSION}.y"
ARCHIVER_COMMUNITY_REVISION = "v${LINUX_VERSION}.${LINUX_SUBVERSION}"

S = "${WORKDIR}/linux-${LINUX_VERSION}.${LINUX_SUBVERSION}"

# ---------------------------------
# Configure devupstream class usage
# ---------------------------------
BBCLASSEXTEND = "devupstream:target"

SRC_URI_class-devupstream = "git://github.com/STMicroelectronics/linux.git;protocol=https;branch=${ARCHIVER_ST_BRANCH}"
SRCREV_class-devupstream = "757c22b1833eb90dc366e14958fb52299cb13c35"

# ---------------------------------
# Configure default preference to manage dynamic selection between tarball and github
# ---------------------------------
STM32MP_SOURCE_SELECTION ?= "tarball"

DEFAULT_PREFERENCE = "${@bb.utils.contains('STM32MP_SOURCE_SELECTION', 'github', '-1', '1', d)}"

# ---------------------------------
# Configure archiver use
# ---------------------------------
include ${@oe.utils.ifelse(d.getVar('ST_ARCHIVER_ENABLE') == '1', 'linux-stm32mp-archiver.inc','')}

# -------------------------------------------------------------
# Defconfig
#
KERNEL_DEFCONFIG        = "defconfig"
KERNEL_CONFIG_FRAGMENTS = "${@bb.utils.contains('KERNEL_DEFCONFIG', 'defconfig', '${S}/arch/arm/configs/fragment-01-multiv7_cleanup.config', '', d)}"
KERNEL_CONFIG_FRAGMENTS += "${@bb.utils.contains('KERNEL_DEFCONFIG', 'defconfig', '${S}/arch/arm/configs/fragment-02-multiv7_addons.config', '', d)}"
KERNEL_CONFIG_FRAGMENTS += "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '${WORKDIR}/fragments/${LINUX_VERSION}/fragment-03-systemd.config', '', d)} "
KERNEL_CONFIG_FRAGMENTS += "${WORKDIR}/fragments/${LINUX_VERSION}/fragment-04-modules.config"
KERNEL_CONFIG_FRAGMENTS += "${@oe.utils.ifelse(d.getVar('KERNEL_SIGN_ENABLE') == '1', '${WORKDIR}/fragments/${LINUX_VERSION}/fragment-05-signature.config','')} "

SRC_URI += "file://${LINUX_VERSION}/fragment-03-systemd.config;subdir=fragments"
SRC_URI += "file://${LINUX_VERSION}/fragment-04-modules.config;subdir=fragments"
SRC_URI += "file://${LINUX_VERSION}/fragment-05-signature.config;subdir=fragments"

# Don't forget to add/del for devupstream
SRC_URI_class-devupstream += "file://${LINUX_VERSION}/fragment-03-systemd.config;subdir=fragments"
SRC_URI_class-devupstream += "file://${LINUX_VERSION}/fragment-04-modules.config;subdir=fragments"
SRC_URI_class-devupstream += "file://${LINUX_VERSION}/fragment-05-signature.config;subdir=fragments"

# -------------------------------------------------------------
# Kernel Args
#
KERNEL_EXTRA_ARGS += "LOADADDR=${ST_KERNEL_LOADADDR}"
