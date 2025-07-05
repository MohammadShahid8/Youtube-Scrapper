// Theme toggle
document.addEventListener('DOMContentLoaded', () => {
    const themeToggle = document.getElementById('theme-toggle');
    const prefersDarkScheme = window.matchMedia('(prefers-color-scheme: dark)');
    const currentTheme = localStorage.getItem('theme');
    
    // Set initial theme
    if (currentTheme === 'dark' || (!currentTheme && prefersDarkScheme.matches)) {
        document.documentElement.classList.add('dark');
    }
    
    // Toggle theme
    themeToggle.addEventListener('click', () => {
        document.documentElement.classList.toggle('dark');
        localStorage.setItem('theme', document.documentElement.classList.contains('dark') ? 'dark' : 'light');
    });
    
    // Form validation
    const form = document.querySelector('form');
    if (form) {
        form.addEventListener('submit', (e) => {
            const urlInput = document.getElementById('url');
            if (!urlInput.value.includes('youtube.com') && !urlInput.value.includes('youtu.be')) {
                e.preventDefault();
                alert('Please enter a valid YouTube URL');
                urlInput.focus();
            }
        });
    }
});