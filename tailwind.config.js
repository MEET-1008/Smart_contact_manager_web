/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/main/resources/**/*.{html,js,css}"],
  theme: {
    theme: {
      colors: {
        transparent: 'transparent',
        current: 'currentColor',
        'white': '#ffffff',
        'purple': '#3f3cbb',
        'midnight': '#121063',
        'metal': '#565584',
        'tahiti': '#3ab7bf',
        'silver': '#ecebff',
        'bubble-gum': '#ff77e9',
        'bermuda': '#78dcca',
      },
      screens: {
        xs: '320px',
        sm: '480px',
        md: '768px',
        lg: '976px',
        xl: '1440px',
      },
      fontFamily: {
        'sans': ['ui-sans-serif', 'system-ui',],
        'serif': ['ui-serif', 'Georgia',],
        'mono': ['ui-monospace', 'SFMono-Regular', ],
        'display': ['Oswald',],
        'body': ['"Open Sans"',],
      }
    },
  },

  plugins: [],
  darkMode:'selector'
}

