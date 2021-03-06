const hasStringInvalidSymbols = (string, invalidSymbolsRegex) => invalidSymbolsRegex.test(string);
    
const hasStringValidSymbols = (string, invalidSymbolsRegex) => !invalidSymbolsRegex.test(string);

const isStringLengthBetween = (currentString, minLength, maxLength) => {
    return currentString.length >= minLength && currentString.length <= maxLength;
}

const isStringAlpha = string => !/[^a-zA-ZáéíóúñÁÉÍÓÚÑ]/.test(string);

const isStringAlphaOrSpaces = string => !/[^a-zA-ZáéíóúñÁÉÍÓÚÑ ]/.test(string)

const isStringNumeric = string => !/[^0-9]/.test(string);

const isStringAlphaNumeric = string => !/[^0-9a-zA-ZáéíóúñÁÉÍÓÚÑ]/.test(string);

const isStringAlphaNumericOrSpaces = string => !/[^0-9a-zA-ZáéíóúñÁÉÍÓÚÑ ]/.test(string);

const isStringValidEmail = string => /^[+a-zA-ZñÑ0-9_.-]+@[a-zA-Z0-9]+(\.[A-Za-z]+)+$/.test(string);

const isString = string => typeof(string) === 'string';

export {
    hasStringInvalidSymbols, 
    hasStringValidSymbols,
    isStringLengthBetween,
    isStringAlpha,
    isStringAlphaOrSpaces,
    isStringNumeric,
    isStringAlphaNumeric,
    isStringAlphaNumericOrSpaces,
    isStringValidEmail,
    isString
}