function handleSignupRequest(xhr, status, args) {
    if (args.validationFailed) {
        return;
    }
    var captcha = window["registrationControllerCaptcha"];
    captcha.ReloadImage();
}

function hideBackOnLastTab() {
    if(PF('wiz').getStepIndex(PF('wiz').currentStep) > 0) {
        PF('wiz').backNav.css("visibility", "hidden")
    }
}

function alertExaminee() {
    PF('dlgWidget').show();
}

function countdown(elementName, minutes, seconds) {

    var element, endTime, hours, mins, msLeft, time;

    function twoDigits(n) {
        return (n <= 9 ? "0" + n : n);
    }

    function updateTimer() {
        msLeft = endTime - (+new Date);
        if (msLeft < 1000) {
            element.innerHTML = "Examination Over!";
            PF('alertTimeWidget').show();
        } else {
            time = new Date(msLeft);
            hours = time.getUTCHours();
            mins = time.getUTCMinutes();
            element.innerHTML = (hours ? hours + ':' + twoDigits(mins) : mins) + ':' + twoDigits(time.getUTCSeconds());
            setTimeout(updateTimer, time.getUTCMilliseconds() + 500);
        }
    }

    element = document.getElementById(elementName);
    endTime = (+new Date) + 1000 * (60 * minutes + seconds) + 500;
    updateTimer();
}