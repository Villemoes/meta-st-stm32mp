#
# Archiver Configuration
#
SRC_URI:append = " file://README.HOW_TO.txt "
COPYLEFT_LICENSE_INCLUDE:append:stm32mpcommon = " Proprietary "
COPYLEFT_LICENSE_EXCLUDE:remove:stm32mpcommon = " Proprietary "

inherit archiver
ARCHIVER_MODE[src] = "original"

inherit archiver_stm32mp_clean
